package org.example.domain;

import lombok.Data;
import org.example.domain.ext.Metadata;

/**
 * @date : 2021/8/9
 */
@Data
public class Product {

    private Long id;

    private String name;

    private Metadata metadata;
}
