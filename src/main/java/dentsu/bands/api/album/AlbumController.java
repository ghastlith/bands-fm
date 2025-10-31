package dentsu.bands.api.album;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dentsu.bands.external.model.Album;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Controller
@RequestMapping("/bands")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    @ResponseBody
    public List<Album> getAll(
        @RequestParam(name = "order", defaultValue = "unordered") String orderParam,
        @Nullable @RequestParam("filter[name]") String filter
    ) {
        val order = AlbumOrder.fromValue(orderParam);
        return albumService.getAll(order, filter);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Album getById(@PathVariable UUID id) {
        return albumService.getById(id);
    }

}
