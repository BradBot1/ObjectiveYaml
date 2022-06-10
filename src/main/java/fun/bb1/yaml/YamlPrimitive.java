package fun.bb1.yaml;

import org.jetbrains.annotations.NotNull;

public final class YamlPrimitive implements IYamlElement {
	
	private final @NotNull Object innerPrimitive;
	
	private @NotNull String comment = "";
	
	public YamlPrimitive(@NotNull final String given) {
		this.innerPrimitive = given;
	}
	
	public YamlPrimitive(@NotNull final Number given) {
		this.innerPrimitive = given;
	}
	
	public YamlPrimitive(@NotNull final char given) {
		this.innerPrimitive = given;
	}
	
	public YamlPrimitive(@NotNull final boolean given) {
		this.innerPrimitive = given;
	}
	
	@Override
	public final String toString() {
		return "YamlPrimitive{innerPrimitive=" + this.innerPrimitive.toString() + ",comment=" + this.comment + "}";
	}
	
	@Override
	public final @NotNull String getAsString() {
		if (this.innerPrimitive instanceof String str) return str;
		if (this.innerPrimitive instanceof Boolean bool) return Boolean.toString(bool);
		if (this.innerPrimitive instanceof Character cha) return new StringBuilder(cha).toString();
		if (this.innerPrimitive instanceof Number num) {
			if (num instanceof Integer in) return Integer.toString(in);
			if (num instanceof Double in) return Double.toString(in);
			if (num instanceof Float in) return Float.toString(in);
			if (num instanceof Long in) return Long.toString(in);
			if (num instanceof Byte in) return Byte.toString(in);
			if (num instanceof Short in) return Short.toString(in);
			return Double.toString(num.doubleValue());
		}
		return this.innerPrimitive.toString();
	}
	/**
	 * Forwards to {@link #getAsBoolean(boolean)} with false as the fallback case
	 */
	public final boolean getAsBoolean() {
		return this.getAsBoolean(false);
	}
	
	public final boolean getAsBoolean(final boolean fallbackCase) {
		if (this.innerPrimitive instanceof Boolean bool) return bool.booleanValue();
		if (this.innerPrimitive instanceof Number num) return num.longValue()>=1;
		if (this.innerPrimitive instanceof Character cha) return Character.toLowerCase(cha) == 'y' || Character.toLowerCase(cha) == 't';
		if (this.innerPrimitive instanceof String str) return str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("y") || str.equalsIgnoreCase("t");
		return fallbackCase;
	}
	
	public final @NotNull Number getAsNumber() {
		if (this.innerPrimitive instanceof Number num) return num;
		if (this.innerPrimitive instanceof Character cha) return (long) cha.charValue();
		if (this.innerPrimitive instanceof Boolean bool) return bool ? 1 : 0;
		if (this.innerPrimitive instanceof String str) {
			try {
				return str.contains(".") ? Double.parseDouble(str) : Long.parseLong(str);
			} catch (Throwable e) {
				return str.length();
			}
		}
		return 0;
	}
	
	public final char getAsCharacter() {
		if (this.innerPrimitive instanceof Character cha) return cha.charValue();
		if (this.innerPrimitive instanceof Number num) return (char) num.longValue();
		if (this.innerPrimitive instanceof String str) return str.length()>0 ? str.charAt(0) : (char) 0;
		if (this.innerPrimitive instanceof Boolean bool) return bool ? 't' : 'f';
		return (char) 0;
	}
	
	public final boolean isString() {
		return this.innerPrimitive instanceof String;
	}
	
	public final boolean isBoolean() {
		return this.innerPrimitive instanceof Boolean;
	}
	
	public final boolean isNumber() {
		return this.innerPrimitive instanceof Number;
	}
	
	public final boolean isCharacter() {
		return this.innerPrimitive instanceof Character;
	}
	
	@Override
	public final boolean equals(Object obj) {
		return this.innerPrimitive.equals(obj instanceof YamlPrimitive prim ? prim.getInner() : obj); 
	}
	
	public final @NotNull Object getInner() {
		return this.innerPrimitive;
	}

	@Override
	public @NotNull String getComment() {
		return this.comment;
	}

	@Override
	public YamlPrimitive setComment(@NotNull final String comment) {
		this.comment = comment;
		return this;
	}
	
}
