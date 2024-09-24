package com.photoChallenger.tripture.global.elasticSearch;

import com.photoChallenger.tripture.domain.item.entity.Item;
import com.photoChallenger.tripture.domain.item.repository.ItemRepository;
import com.photoChallenger.tripture.domain.post.entity.Post;
import com.photoChallenger.tripture.domain.post.repository.PostRepository;
import com.photoChallenger.tripture.global.elasticSearch.challengeSearch.ChallengeSearchService;
import com.photoChallenger.tripture.global.elasticSearch.itemSearch.ItemSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ElasticSearchController {
    private final ChallengeSearchService challengeSearchService;
    private final ItemSearchService itemSearchService;
    private final PostRepository postRepository;
    private final ItemRepository itemRepository;
//    @PostMapping("elasticSearch/challenge")
    public ResponseEntity<String> createChallenge() {
        List<Post> posts = postRepository.findAll();
        for(Post post:posts){
            challengeSearchService.createItem(post);
        }
        return ResponseEntity.ok("success");
    }

//    @PostMapping("elasticSearch/item")
    public ResponseEntity<String> createItem() {
        List<Item> items = itemRepository.findAll();
        for(Item item:items){
            itemSearchService.createItem(item);
        }
        return ResponseEntity.ok("success");
    }
}
