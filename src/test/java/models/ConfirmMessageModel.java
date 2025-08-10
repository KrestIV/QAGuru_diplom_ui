package models;

import lombok.Data;

@Data
public class ConfirmMessageModel {
    String code, type, message;
}
