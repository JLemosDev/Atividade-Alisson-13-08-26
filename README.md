Atividade de Generics e Comparação em Java

Exercício 7 — Generics Avançados

Foram implementados métodos genéricos para calcular soma, média e maior valor de coleções numéricas.

Foi utilizado "? extends Number" para trabalhar com coleções produtoras e "? super" para coleções consumidoras.

Também foi utilizado:

<T extends Number & Comparable<T>>

para limitar os tipos utilizados e permitir a comparação dos valores.

Regra PECS

PECS significa Producer Extends, Consumer Super.

- "? extends" → usado quando queremos ler dados de uma coleção.
- "? super" → usado quando queremos adicionar dados a uma coleção.

---

Exercício 8 — Comparable e Comparator

Foi criada a classe "Funcionario", contendo nome, salário e setor.

A classe implementa "Comparable<Funcionario>", definindo a ordenação natural pelo nome.

Também foram criados "Comparator<Funcionario>" para ordenar por:

- Salário crescente;
- Salário decrescente;
- Setor.

O "Comparable" é indicado quando existe uma ordenação natural para a classe. Já o "Comparator" permite criar diferentes critérios de ordenação sem alterar a classe.

---

Exercício 9 — Framework Genérico de Validação

Foi criada uma API de validação utilizando Generics.

A interface funcional "Regra<T>" representa as regras de validação, enquanto "Validador<T>" permite aplicar várias regras a diferentes tipos de objetos.

O sistema retorna uma lista com as mensagens de erro encontradas, sem lançar exceções.

A solução favorece a reutilização, pois o mesmo "Validador<T>" pode ser utilizado para clientes, produtos, pedidos e outros objetos, mantendo a segurança de tipos.
