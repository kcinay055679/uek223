package ch.bbt.uek223.ticketshop;

public interface Mapper <E, DTO>{
    DTO toDto(E e);
    E toEntity(DTO dto);
}
