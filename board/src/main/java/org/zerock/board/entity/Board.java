package org.zerock.board.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Board extends BaseEntity {

    @Id @GeneratedValue
    private Long bno;

    private String title;

    private String content;

    // 작성자는 아직 처리하지 않음.
    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;
}
