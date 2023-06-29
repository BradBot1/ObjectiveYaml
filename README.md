# Objective YAML

A `Java 17` GSON like approach to YAML

> This project utilises [eo-yaml](https://github.com/decorators-squad/eo-yaml) internally

> Serialization of an object is not supported, this simply replicates the functionality of JsonElement, JsonObject, JsonArray and JsonPrimitive from GSON for YAML

## How to use

Simply import `fun.bb1.yaml.Yaml` and invoke `#parseString()` with your YAML.

```java

import fun.bb1.yaml.Yaml;

...

YamlObject yaml = Yaml.parseString("example: 1", YamlObject.class);
```

You should always use `YamlObject` by default, but this method can parse other types if needed.
