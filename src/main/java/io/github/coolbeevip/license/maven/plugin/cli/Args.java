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

import com.beust.jcommander.Parameter;

public class Args {
  @Parameter(names = "-path", description = "CSV文件所在的目录", required = true)
  public String path;

  @Parameter(names = "-output", description = "合并后的文件名", required = true)
  public String output;

  @Parameter(names = "-separator", description = "CSV 分隔符")
  public String separator = "\t";
}
