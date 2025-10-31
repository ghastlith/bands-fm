package dentsu.bands.api.band;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dentsu.bands.external.model.Band;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bands")
@RequiredArgsConstructor
public class BandController {

    private final BandService bandService;

    @GetMapping
    @ResponseBody
    public List<Band> getAll(
        @RequestParam(defaultValue = "unordered") String order,
        @Nullable @RequestParam("filter[name]") String filter
    ) {
        return bandService.getAll(order, filter);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Band getById(@PathVariable UUID id) {
        return bandService.getById(id);
    }

}
