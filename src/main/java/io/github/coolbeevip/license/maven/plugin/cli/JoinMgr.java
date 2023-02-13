/**
 * Copyright © 2020 Lei Zhang (zhanglei@apache.org)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.coolbeevip.license.maven.plugin.cli;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class JoinMgr {

  private Map<String, Map<String, Artifact>> csvArtifactMap = new HashMap<>();

  private final String separator;

  public JoinMgr(String separator) {
    this.separator = separator;
  }

  public void addCsv(Path csvPath) throws IOException {
    Map<String, Artifact> artifactMap = new HashMap<>();
    try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvPath.toFile()))
        .withCSVParser(new CSVParserBuilder()
            .withSeparator(separator.charAt(0))
            .build())
        .withSkipLines(1)
        .build()) {
      String[] values;
      while ((values = csvReader.readNext()) != null) {
        Artifact artifact = new Artifact(values[0], values[1], values[2]);
        artifactMap.put(artifact.getKey(), artifact);
      }
    } catch (CsvValidationException e) {
      throw new RuntimeException(e);
    }
    this.csvArtifactMap.put(csvPath.getFileName().toString(), artifactMap);
  }

  public void join(Path outputPath) throws IOException {
    Set<String> keys = new TreeSet();
    csvArtifactMap.values().forEach(e -> {
      e.keySet().forEach(k -> keys.add(k));
    });

    String[][] versions = new String[keys.size()][csvArtifactMap.keySet().size() + 1];

    AtomicInteger keyIdx = new AtomicInteger();
    keys.forEach(key -> {
      log.info("=> {}", key);
      AtomicInteger fileIdx = new AtomicInteger();
      versions[keyIdx.get()][fileIdx.getAndIncrement()] = key;

      csvArtifactMap.forEach((fileName, artifact) -> {
        String version = "";
        if (artifact.containsKey(key)) {
          version = artifact.get(key).getVersion();
        }
        versions[keyIdx.get()][fileIdx.get()] = version;
        fileIdx.getAndIncrement();
      });
      keyIdx.incrementAndGet();
    });

    FileWriter fileWriter = new FileWriter(Paths.get(outputPath.toString(), "JOINED.CSV").toFile());
    try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
      printWriter.println("artifact\t" + csvArtifactMap.keySet().stream().collect(Collectors.joining("\t")));
      for (String[] line : versions) {
        printWriter.print(Arrays.stream(line).collect(Collectors.joining("\t")) + "\n");
      }
    }
  }
}
