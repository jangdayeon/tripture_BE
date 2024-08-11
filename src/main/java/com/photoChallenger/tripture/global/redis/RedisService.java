package com.photoChallenger.tripture.global.redis;

import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.global.exception.post.NoSuchPostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final PostRepository postRepository;

    @Transactional
    @Scheduled(cron = "0 0/5 * * * *")
    public void updateDBFromRedis() {
        ScanOptions options = ScanOptions.scanOptions()
                .match("post:*")
                .count(10)
                .build();
        Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
                connection -> connection.scan(options)
        );
        if (cursor != null) {
            while (cursor.hasNext()) {
                String key = new String(cursor.next());
                Long postId = Long.parseLong(key.split(":")[1]);
                String view = redisTemplate.opsForValue().get(key);
                log.info(view);
                if (view != null) {
                    Post post = postRepository.findById(postId).orElseThrow(NoSuchPostException::new);
                    post.viewCountRedis(Long.valueOf(view));
                }
            }
        }
    }
}
