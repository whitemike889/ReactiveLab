package io.reactivex.lab.services.impls;

import io.reactivex.lab.services.MiddleTierService;
import io.reactivex.lab.services.common.SimpleJson;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.text.sse.ServerSentEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class RatingsService extends MiddleTierService {

    @Override
    protected Observable<Void> handleRequest(HttpServerRequest<?> request, HttpServerResponse<ServerSentEvent> response) {
        List<String> videoIds = request.getQueryParameters().get("videoId");
        return Observable.from(videoIds).map(videoId -> {
            Map<String, Object> video = new HashMap<>();
            video.put("videoId", videoId);
            video.put("estimated_user_rating", 3.5);
            video.put("actual_user_rating", 4);
            video.put("average_user_rating", 3.1);
            return video;
        }).flatMap(video -> {
            return response.writeAndFlush(new ServerSentEvent("", "data", SimpleJson.mapToJson(video)));
        }).delay(20, TimeUnit.MILLISECONDS); // simulate latenc
    }
}
