package com.rsupprot.board.restAPI;

import com.rsupprot.board.dto.PostsMainResponseDto;
import com.rsupprot.board.dto.PostsPagingDto;
import com.rsupprot.board.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RestPageController {
    private final PostsService postsService;

    @GetMapping("/totalPage")
    public long totalPage(){
        return postsService.totalPageCount(10);
    }

    @GetMapping("/paging")
    public List<PostsMainResponseDto> Paging(PostsPagingDto dto){
        return postsService.findAllDesc(dto.getRequirePage(),dto.getPrintListSize());
    }
}
