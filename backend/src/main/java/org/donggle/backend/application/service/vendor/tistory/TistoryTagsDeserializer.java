package org.donggle.backend.application.service.vendor.tistory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.donggle.backend.application.service.vendor.tistory.dto.response.TistoryTags;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TistoryTagsDeserializer extends JsonDeserializer<TistoryTags> {
    @Override
    public TistoryTags deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final JsonNode tagsNode = node.get("tag");

        final List<String> tags = Optional.ofNullable(tagsNode)
                .stream()
                .flatMap(tag -> StreamSupport.stream(tag.spliterator(), false))
                .filter(JsonNode::isTextual)
                .map(JsonNode::textValue)
                .collect(Collectors.toList());

        return new TistoryTags(tags);
    }
}