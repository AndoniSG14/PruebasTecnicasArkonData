

CREATE TABLE `tb_boletos` (
  `id` int(11) NOT NULL,
  `canjeado` varchar(255) DEFAULT NULL,
  `idevento` int(11) DEFAULT NULL,
  `numero_boleto` int(11) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `fecha_canjeado` varchar(255) DEFAULT NULL,
  `fecha_venta` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `tb_boletos` (`id`, `canjeado`, `idevento`, `numero_boleto`, `token`, `fecha_canjeado`, `fecha_venta`) VALUES
(1, '1', 1, 102, 'IEfNEMszaCOeBoLi', '2025-02-05', '2025-02-05'),
(8, '1', 1, 103, 'VhXX98BkdUnguJKX', '2025-02-06', '2025-02-05');
CREATE TABLE `tb_eventos` (
  `id` int(11) NOT NULL,
  `boletos_maximos` int(11) DEFAULT NULL,
  `boletos_vendidos` int(11) DEFAULT NULL,
  `fecha_fin` varchar(255) DEFAULT NULL,
  `fecha_incio` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `tb_eventos` (`id`, `boletos_maximos`, `boletos_vendidos`, `fecha_fin`, `fecha_incio`, `nombre`) VALUES
(1, 200, 103, '2025-02-10', '2025-02-03', 'Otro evento');

ALTER TABLE `tb_boletos`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `tb_eventos`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `tb_boletos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

ALTER TABLE `tb_eventos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;
