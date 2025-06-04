package com.bt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto{
    private long id;

    @NotBlank(message = "First name can not be empty")
    @Size(min = 3, max = 50, message = "First name length around 3 to 50")
    private String firstName;
    @Size(min = 3, max = 50, message = "Last name length around 3 to 50")
    @NotBlank(message = "Last name can not be empty")
    private String lastName;
    private String phone;
    private String address;
    private MultipartFile file;
    private String image;
}
