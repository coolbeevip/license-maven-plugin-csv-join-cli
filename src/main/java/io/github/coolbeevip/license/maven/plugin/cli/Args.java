package io.github.coolbeevip.license.maven.plugin.cli;

import com.beust.jcommander.Parameter;

public class Args {
  @Parameter(names = "-path", description = "CSV文件所在的目录", required = true)
  public String path;

  @Parameter(names = "-output", description = "合并后的文件名", required = true)
  public String output;

  @Parameter(names = "-separator", description = "CSV 分隔符")
  public String separator = "\t";
}
