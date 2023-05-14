package space.cuongnh2k.rest.account.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExtractAccountReq {
    private List<String> emails;

    private List<String> ids;
}
