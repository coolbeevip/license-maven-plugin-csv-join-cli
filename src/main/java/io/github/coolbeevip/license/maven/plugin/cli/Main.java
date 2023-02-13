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
      if (file.getName().endsWith(".csv")) {
        mgr.addCsv(file.toPath());
      }
    }
    mgr.join(Paths.get(cliArgs.output));
  }
}
