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

import com.beust.jcommander.JCommander;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
  public static void main(String[] args) throws IOException {
    Args cliArgs = new Args();
    JCommander.newBuilder()
        .addObject(cliArgs)
        .build()
        .parse(args);

    JoinMgr mgr = new JoinMgr(cliArgs.separator);
    File[] csvFiles = Paths.get(cliArgs.path).toFile().listFiles();
    for (File file : csvFiles) {
      if (file.getName().toLowerCase().endsWith(".csv") && file.getName().toLowerCase().indexOf("joined") == -1) {
        mgr.addCsv(file.toPath());
      }
    }
    mgr.join(Paths.get(cliArgs.output));
  }
}
