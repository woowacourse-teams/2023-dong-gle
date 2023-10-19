package org.donggle.backend.application.service.publish;

import lombok.RequiredArgsConstructor;
import org.donggle.backend.application.client.FileDownloadClient;
import org.donggle.backend.application.service.request.ImageUploadRequest;
import org.donggle.backend.application.service.request.PublishRequest;
import org.donggle.backend.application.service.request.PublishWritingRequest;
import org.donggle.backend.domain.blog.Blog;
import org.donggle.backend.domain.blog.BlogClients;
import org.donggle.backend.domain.blog.BlogType;
import org.donggle.backend.domain.renderer.html.HtmlRenderer;
import org.donggle.backend.domain.writing.BlockType;
import org.donggle.backend.domain.writing.Writing;
import org.donggle.backend.domain.writing.block.Block;
import org.donggle.backend.domain.writing.block.Depth;
import org.donggle.backend.domain.writing.block.ImageBlock;
import org.donggle.backend.domain.writing.block.ImageUrl;
import org.donggle.backend.domain.writing.block.NormalBlock;
import org.donggle.backend.domain.writing.block.RawText;
import org.donggle.backend.ui.response.ImageUploadResponse;
import org.donggle.backend.ui.response.PublishResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublishFacadeService {
    private final PublishService publishService;
    private final HtmlRenderer htmlRenderer;
    private final BlogClients blogClients;
    private final FileDownloadClient fileDownloadClient;

    public void publishWriting(final Long memberId, final Long writingId, final BlogType blogType, final PublishRequest publishRequest) {
        final PublishWritingRequest request = publishService.findPublishWriting(memberId, writingId, blogType);
        final Blog blog = request.blog();
        final Writing writing = request.writing();
        List<Block> blocks = writing.getBlocks();

        if (blogType == BlogType.TISTORY) {
            blocks = replaceTistoryImage(blogType, blocks, request.accessToken());
        }

        final String content = htmlRenderer.render(blocks);

        final PublishResponse response = blogClients.publish(blogType, publishRequest, content, request.accessToken(), writing.getTitleValue());
        publishService.saveProperties(blog, writing, response);
    }

    private List<Block> replaceTistoryImage(final BlogType blogType, final List<Block> blocks, final String accessToken) {
        final List<Block> replacedBlocks = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getBlockType() == BlockType.IMAGE) {
                final ImageBlock imageBlock = (ImageBlock) blocks.get(i);
                final ImageUrl imageUrl = imageBlock.getImageUrl();
                final Flux<DataBuffer> imageData = fileDownloadClient.downloadAsFlux(imageUrl.getImageUrl());
                final MediaType mediaType = parseMediaTypeFromFileName(imageUrl.getImageUrl());
                final ImageUploadRequest imageUploadRequest = new ImageUploadRequest(imageData, mediaType);
                final ImageUploadResponse imageUploadResponse = blogClients.uploadImage(blogType, accessToken, imageUploadRequest);
                final NormalBlock imageReplacer = new NormalBlock(Depth.empty(), BlockType.PARAGRAPH, RawText.from(imageUploadResponse.replacer()), List.of());
                replacedBlocks.add(imageReplacer);
            } else {
                replacedBlocks.add(blocks.get(i));
            }
        }
        return replacedBlocks;
    }

    private MediaType parseMediaTypeFromFileName(final String fileName) {
        return Optional.ofNullable(URLConnection.guessContentTypeFromName(fileName))
                .map(MediaType::parseMediaType)
                .orElseGet(() -> MediaType.APPLICATION_OCTET_STREAM);
    }
}
