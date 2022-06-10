package fun.bb1.yaml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class YamlArray implements IYamlElement, Iterable<IYamlElement> {
	
	private final @NotNull List<IYamlElement> elementList = new LinkedList<IYamlElement>();
	
	private @NotNull String comment = "";
	
	public YamlArray(@Nullable final IYamlElement... elements) {
		if (elements != null && elements.length > 0) {
			for (final IYamlElement element : elements) {
				if (element == null) continue;
				this.elementList.add(element);
			}
		}
	}
	
	public final void add(@NotNull final IYamlElement element) {
		if (element == this || (element instanceof YamlArray arr && arr.contains(this))) return;
		this.elementList.add(element);
	}
	
	public final void add(final int index, @NotNull final IYamlElement element) {
		this.elementList.add(index, element);
	}
	
	public final @Nullable IYamlElement get(final int index) {
		if (index > this.elementList.size()) return null;
		return this.elementList.get(index);
	}
	
	public final int getSize() {
		return this.elementList.size();
	}
	
	public final boolean contains(@NotNull final IYamlElement element) {
		return this.elementList.contains(element);
	}
	
	@Override
	public Iterator<IYamlElement> iterator() {
		return this.elementList.iterator();
	}

	@Override
	public @NotNull String getComment() {
		return this.comment;
	}

	@Override
	public YamlArray setComment(@NotNull final String comment) {
		this.comment = comment;
		return this;
	}
	
	@Override
	public String toString() {
		return "YamlArray{elementList=[" + String.join(",", this.elementList.stream().collect(()->new LinkedList<String>(), (a,b)->a.add(b instanceof YamlArray arr ? "YamlArray[" + arr.getSize() + "]" : b.getAsString()), (a,b)->a.addAll(b))) + "],comment=" + this.comment + "}";
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof YamlArray y ? y.elementList.containsAll(this.elementList) && this.elementList.containsAll(y.elementList) : this.toString().equals(obj.toString());
	}
	
}
