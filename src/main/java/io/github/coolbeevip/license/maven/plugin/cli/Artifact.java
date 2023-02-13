package io.github.coolbeevip.license.maven.plugin.cli;

public class Artifact {
  private final String artifactId;
  private final String groupId;
  private final String version;

  public Artifact(String groupId, String artifactId, String version) {
    this.artifactId = artifactId;
    this.groupId = groupId;
    this.version = version;
  }

  public String getKey() {
    return this.groupId + "»" + this.artifactId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getGroupId() {
    return groupId;
  }

  public String getVersion() {
    return version;
  }
}
