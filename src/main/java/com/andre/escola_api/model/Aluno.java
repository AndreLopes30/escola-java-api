package com.andre.escola_api.model;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_alunos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Max(10)
    private Double nota;

    @Column
    private String turma;

    @Column
    @Min(0)
    private Integer idade;


}
