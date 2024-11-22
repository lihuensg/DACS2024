package utn.dacs.ms.conector.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibroResponseDto {
    private int available;
    private int number;
    private int offset;

    private List<List<LibroDto>> books;
   
}