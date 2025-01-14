package ru.akvine.profiley.rest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.akvine.profiley.rest.dto.common.Response;
import ru.akvine.profiley.rest.dto.dictionaries.CreateDictionaryRequest;
import ru.akvine.profiley.rest.dto.dictionaries.UpdateDictionaryRequest;

@RequestMapping(value = "/dictionaries")
public interface DictionaryControllerMeta {
    @GetMapping
    Response list();

    @PostMapping(value = "/by-json")
    Response createByJson(@RequestBody @Valid CreateDictionaryRequest request);

    @PostMapping(value = "/by-file")
    Response createByFile(@RequestParam("file") MultipartFile file,
                          @RequestParam("separator") String separator,
                          @RequestParam("locale") String locale,
                          @RequestParam("domainName") String domainName);

    @PatchMapping(value = "/by-json")
    Response updateByJson(@RequestBody @Valid UpdateDictionaryRequest request);

    @DeleteMapping(value = "/{uuid}")
    Response delete(@PathVariable("uuid") String uuid);
}
