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
@Table(name = "upeu_cliente")
public class Cliente {
    @Id
    @Column(name = "dniruc", nullable = false, length = 12)
    private String dniruc; // Este será "Medida"

    @Column(name = "nombres", nullable = false, length = 160)
    private String nombres; // Este será "Tipo de Banda"

    @Column(name = "rep_legal", length = 160)
    private String repLegal; // Este será "Fecha"

    @Column(name = "tipo_documento", nullable = false, length = 12)
    private String tipoDocumento; // Este será "Costo"
}