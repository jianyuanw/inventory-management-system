package sg.edu.iss.ims.item;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.transaction.Transaction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Reorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    private int quantity;

    private LocalDate orderDate;

    private ReorderStatus status;

    private LocalDate receivedDate;

    @OneToOne
    private Transaction transaction;
}
