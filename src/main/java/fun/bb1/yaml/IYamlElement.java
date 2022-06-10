package fun.bb1.yaml;

import org.jetbrains.annotations.NotNull;

public sealed interface IYamlElement permits YamlPrimitive, YamlObject, YamlArray {
	
	public default boolean isYamlPrimitive() {
		return this instanceof YamlPrimitive;
	}
	
	public default @NotNull YamlPrimitive getAsYamlPrimitive() {
		return (YamlPrimitive) this;
	}
	
	public default boolean isYamlObject() {
		return this instanceof YamlObject;
	}
	
	public default @NotNull YamlObject getAsYamlObject() {
		return (YamlObject) this;
	}
	
	public default boolean isYamlArray() {
		return this instanceof YamlArray;
	}
	
	public default @NotNull YamlArray getAsYamlArray() {
		return (YamlArray) this;
	}
	
	public default @NotNull String getAsString() {
		return this.toString();
	}
	
	public @NotNull String getComment();
	
	public IYamlElement setComment(@NotNull final String comment);
	
}
