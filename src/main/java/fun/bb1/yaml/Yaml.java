package fun.bb1.yaml;

import static fun.bb1.exceptions.handler.ExceptionHandler.handle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.amihaiemil.eoyaml.Scalar;
import com.amihaiemil.eoyaml.YamlInput;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlMappingBuilder;
import com.amihaiemil.eoyaml.YamlNode;
import com.amihaiemil.eoyaml.YamlScalarBuilder;
import com.amihaiemil.eoyaml.YamlSequence;
import com.amihaiemil.eoyaml.YamlSequenceBuilder;

public final class Yaml {
	
	private Yaml() {
		
	}
	
	@SuppressWarnings("unchecked") // can be suppressed as types are checked
	public static final <T extends IYamlElement> @NotNull T parseString(@NotNull final String yamlString, Class<T> parseType) {
		final YamlInput input = com.amihaiemil.eoyaml.Yaml.createYamlInput(yamlString);
		if (parseType == YamlPrimitive.class) {
			final Scalar scalar = handle(()->input.readPlainScalar(),
								  handle(()->input.readFoldedBlockScalar(),
								  handle(()->input.readLiteralBlockScalar())));
			if (scalar == null) return (T) new YamlPrimitive("");
			return (T) recurse(scalar);
		} else if (parseType == YamlObject.class) {
			final YamlMapping mapping = handle(()->input.readYamlMapping());
			if (mapping == null) return (T) new YamlObject();
			return (T) recurse(mapping);
		} else {
			final YamlSequence sequence = handle(()->input.readYamlSequence());
			if (sequence == null) return (T) new YamlArray();
			return (T) recurse(sequence);
		}
	}
	
	private static final @NotNull IYamlElement recurse(@NotNull final YamlNode yamlNode) {
		if (yamlNode instanceof Scalar scalar) return new YamlPrimitive(scalar.value()).setComment(scalar.comment().value());
		if (yamlNode instanceof YamlMapping mapping) {
			final YamlObject object = new YamlObject();
			for (final YamlNode key : mapping.keys()) {
				final YamlNode value = mapping.value(key);
				final IYamlElement recursedKey = recurse(key);
				object.add(recursedKey instanceof YamlPrimitive prim ? prim : new YamlPrimitive(recursedKey.getAsString()), recurse(value));
			}
			return object.setComment(mapping.comment().value());
		}
		final YamlSequence sequence = (YamlSequence) yamlNode;
		final YamlArray array = new YamlArray();
		for (final YamlNode elem : sequence) {
			array.add(recurse(elem));
		}
		return array.setComment(sequence.comment().value());
	}
	
	public static final @NotNull String toYaml(@NotNull final IYamlElement yamlElement) {
		return recurse(yamlElement).toString();
	}
	
	private static final @NotNull YamlNode recurse(@NotNull final IYamlElement yamlElement) {
		if (yamlElement instanceof YamlObject obj) {
			YamlMappingBuilder objBuilder = com.amihaiemil.eoyaml.Yaml.createYamlMappingBuilder();
			for (final YamlPrimitive objKey : obj) {
				objBuilder = objBuilder.add(recurse(objKey), recurse(obj.get(objKey))); // JUST WHY
			}
			return yamlElement.getComment().isEmpty() ? objBuilder.build() : objBuilder.build(yamlElement.getComment());
		}
		if (yamlElement instanceof YamlArray arr) {
			YamlSequenceBuilder arrBuilder = com.amihaiemil.eoyaml.Yaml.createYamlSequenceBuilder();
			for (final IYamlElement arrElem : arr) {
				arrBuilder = arrBuilder.add(recurse(arrElem)); // weird impl thing??
			}
			return arr.getComment().isEmpty() ? arrBuilder.build() : arrBuilder.build(arr.getComment());
		}
		return buildForString(yamlElement.getAsString(), yamlElement.getComment());
	}
	
	private static final @NotNull Scalar buildForString(@NotNull final String str, @Nullable final String comment) {
		final YamlScalarBuilder scalar = com.amihaiemil.eoyaml.Yaml.createYamlScalarBuilder().addLine(str);
		if (comment == null || comment.isEmpty()) {
			if (str.contains("\n")) return scalar.buildFoldedBlockScalar();
			return scalar.buildPlainScalar();
		}
		if (str.contains("\n")) return scalar.buildFoldedBlockScalar(comment);
		return scalar.buildPlainScalar(comment, "");
	}
	
}
