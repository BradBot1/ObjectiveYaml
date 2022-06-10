package fun.bb1.yaml;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class YamlObject implements IYamlElement, Iterable<YamlPrimitive> {
	
	private final @NotNull Map<YamlPrimitive, IYamlElement> elementMap = new LinkedHashMap<YamlPrimitive, IYamlElement>();
	
	private @NotNull String comment = "";
	
	public YamlObject() {
		
	}
	
	public YamlObject(@NotNull final YamlObject yamlObject) {
		this.elementMap.putAll(yamlObject.elementMap);
	}
	
	public YamlObject(@NotNull final Map<YamlPrimitive, IYamlElement> map) {
		this.elementMap.putAll(map);
	}
	
	public void add(@NotNull final YamlPrimitive key, @NotNull final IYamlElement element) {
		this.elementMap.put(key, element);
	}
	
	public void add(@NotNull final String key, @NotNull final IYamlElement element) {
		this.elementMap.put(new YamlPrimitive(key), element);
	}
	
	public void addProperty(@NotNull final String key, @NotNull final String primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void addProperty(@NotNull final String key, @NotNull final Number primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void addProperty(@NotNull final String key, final boolean primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void addProperty(@NotNull final String key, final char primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void addProperty(@NotNull final YamlPrimitive key, @NotNull final String primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void addProperty(@NotNull final YamlPrimitive key, @NotNull final Number primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void addProperty(@NotNull final YamlPrimitive key, final boolean primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void addProperty(@NotNull final YamlPrimitive key, final char primitive) {
		this.add(key, new YamlPrimitive(primitive));
	}
	
	public void remove(@NotNull final YamlPrimitive key) {
		this.elementMap.remove(key);
	}
	
	public void remove(@NotNull final String key) {
		this.remove(new YamlPrimitive(key));
	}
	
	public boolean contains(@NotNull final YamlPrimitive key) {
		return this.elementMap.containsKey(key);
	}
	
	public boolean contains(@NotNull final String key) {
		return this.contains(new YamlPrimitive(key));
	}
	
	public final @Nullable IYamlElement get(@NotNull final YamlPrimitive key) {
		return this.elementMap.get(key);
	}
	
	public final @Nullable IYamlElement get(@NotNull final String key) {
		return this.get(new YamlPrimitive(key));
	}
	
	public final int getSize() {
		return this.elementMap.size();
	}

	@Override
	public Iterator<YamlPrimitive> iterator() {
		return this.elementMap.keySet().iterator();
	}

	@Override
	public @NotNull String getComment() {
		return this.comment;
	}

	@Override
	public YamlObject setComment(@NotNull final String comment) {
		this.comment = comment;
		return this;
	}
	
	@Override
	public String toString() {
		return "YamlObject{elementMap=" + this.elementMap + ",comment=" + this.comment + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof YamlObject y ? y.elementMap.equals(this.elementMap) : this.toString().equals(obj.toString());
	}
	
}
