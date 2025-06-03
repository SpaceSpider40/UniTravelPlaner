package uni.unitravelplaner.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.unitravelplaner.dto.car.CarCreationDto;
import uni.unitravelplaner.dto.user.UserCreationDto;
import uni.unitravelplaner.entity.Car;
import uni.unitravelplaner.entity.User;
import uni.unitravelplaner.service.CarService;
import uni.unitravelplaner.service.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<User> postUser(@RequestBody UserCreationDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping("")
    public Page<User> getUserPage(@RequestParam int page, @RequestParam int size) {
        final var pageRequest = PageRequest.of(page, size);

        return userService.getUserPage(pageRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
