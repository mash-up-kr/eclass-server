package com.mashup.eclassserver.model.entity

import javax.persistence.*

@Entity
data class Cover(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val coverId: Long = 0,
    val petId: Long,
    val imageUrl: String,
    val color: String,
    @Enumerated(EnumType.STRING)
    val shapeType: ShapeType,
    @Column(name ="shape_x")
    val shapeX: Double,
    @Column(name ="shape_y")
    val shapeY: Double
) : BaseEntity()

enum class ShapeType {
    TRIANGLE,
    RECTANGLE,
    CIRCLE
}
