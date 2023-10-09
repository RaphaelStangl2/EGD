package at.htl.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
    Account mailTo;
    String subject;
    String text;
}
