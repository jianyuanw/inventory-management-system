package sg.edu.iss.ims.restock;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.item.Reorder;

@Entity
@Data
@NoArgsConstructor
public class Restock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Reorder reorder;

    private LocalDate receivedDate;



}
