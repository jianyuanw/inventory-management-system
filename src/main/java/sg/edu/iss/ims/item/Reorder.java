package sg.edu.iss.ims.item;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.ims.transaction.Transaction;

@Entity
@Data
@NoArgsConstructor
public class Reorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    @NotNull(message = "Reorder quantity cannot be null")
    @Min(value = 1, message = "Reorder quantity cannot be below 1")
    private int quantity;

    private LocalDate orderDate;

    private ReorderStatus status;

    private LocalDate receivedDate;

    @OneToOne
    private Transaction transaction;
}
