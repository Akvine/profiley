package ru.akvine.profiley.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.services.dto.PossibleDomain;

import java.util.List;

@RequestMapping(value = "/profile")
public interface ProfilerControllerMeta {
    @PostMapping
    List<? extends PossibleDomain> profile(@RequestParam("file") MultipartFile file);
}
