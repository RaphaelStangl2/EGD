package at.htl.Classes;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
public class DateDto {
    Long carId;
    LocalDate fromDate;
    LocalDate toDate;
}
