# Projeto Eventos

v1:

- Inscrição possui uma lista de atividades de determinado evento;
- Inscrição pode possuir um cupom, porem evento não pode;
- O pagamento da inscrição só pode ser feito de uma vez, o valor completo;
- Depois de pago, não se pode mais inscrever em mais atividades;
- Cupom possui regra de desconto geral ou por tipo de atividade (porém ainda com bad smells);
- As classes correspondentes aos eventos e atividades em particular (Palestra, Mesa Redonda, Simpósio etc) ainda não possuem diferenças;
- Classes iniciadas por 'A' são abstratas (Ex. AEvent) e as iniciadas com 'E' são enums (Ex. EStatus);
- Os pacotes estão divididos atualmente em eventos, atividades, enums, outros e testes;
- Os testes estão ![nesta pasta](https://github.com/Spallacety/ProjetoEventos/tree/master/app/src/main/java/br/edu/ifpi/projetoeventos/tests);
- A parte do aplicativo em si ainda não foi feita, apenas a parte do cadastro de e-mail e senha;
- A senha salva passa por um MD5 antes de ir pro database.
