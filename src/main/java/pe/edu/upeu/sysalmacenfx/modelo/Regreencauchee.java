package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reg_reencauche") // Cambié el nombre de la tabla para reflejar que es "regreencauchee"
public class Regreencauchee {
    @Id
    @Column(name = "medida", nullable = false, length = 12) // Aquí "medida" sería tu clave primaria
    private String medida; // Este será el identificador único (similar a "dniruc" en Cliente)

    @Column(name = "tipodeban", nullable = false, length = 160) // "Tipo de Banda"
    private String tipoBanda; // Este será "Tipo de Banda"

    @Column(name = "fecha", length = 160) // "Fecha"
    private String fecha; // Este será "Fecha"

    @Column(name = "costo", nullable = false, length = 12) // "Costo"
    private String costo; // Este será "Costo"


}