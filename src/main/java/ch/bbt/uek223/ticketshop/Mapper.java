package ch.bbt.uek223.ticketshop;

public interface Mapper <E, U>{
    U toDto(E u);

    E toEntity(U u);
}
