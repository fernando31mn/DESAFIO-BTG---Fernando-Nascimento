package dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderMessage {
    private Long codigoPedido;
    private Long codigoCliente;
    private List<Item> itens;

    @Data
    public static class Item {
        private String produto;
        private Integer quantidade;
        private BigDecimal preco;
    }
}
