# Projeto Eventos

v1:

- Inscrição possui uma lista de atividades de determinado evento;
- Inscrição pode possuir um cupom, porem evento não pode;
- O pagamento da inscrição só pode ser feito de uma vez, o valor completo;
- Depois de pago, não se pode mais inscrever em mais atividades;
- Não se pode inscrever em eventos que não estejam em aberto;
- Os pacotes estão divididos atualmente em eventos, cupons, enums, outros e testes;
- Os testes estão ![nesta pasta](https://github.com/Spallacety/ProjetoEventos/tree/master/app/src/test/java/br/edu/ifpi/projetoeventos);
- A parte do aplicativo em si ainda não foi feita, apenas a parte do cadastro de e-mail e senha;
- A senha salva passa por um MD5 antes de ir pro database;
- Projeto feito no Android Studio.

v2:

- Eventos podem agora ser satélites;
- Agora os eventos e atividades tem espaços fisicos;
- Atividades tem horário previsto de inicio e término e podem agora ser obrigatórias ou não;
- Evento possui um dono e um time de usuários auxiliares na gerencia;
- Atividade possui nome e informações do responsável (palestrante, mestre de cerimônia, etc).
- A parte de banco de dados e autenticação está agora no Firebase;
- Os pacotes estão agora redivididos em exceptions, firebase e modelos (cupons, eventos, enums e outros);
- O foco maior foi dado no modelo, e por isso as telas da aplicação não foram completamente feitas.
