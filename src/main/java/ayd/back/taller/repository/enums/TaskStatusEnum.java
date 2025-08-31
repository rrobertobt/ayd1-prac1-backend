package ayd.back.taller.repository.enums;

import lombok.Getter;

@Getter
public enum TaskStatusEnum {

    PENDIENTE("pendiente"),
    INICIADO("iniciado"),
    EN_PROGRESO("en progreso"),
    COMPLETADO("completado"),
    CANCELADO("cancelado");

    private String status;

    TaskStatusEnum(String status) {
        this.status = status;
    }


}
