#!/bin/bash

### TOPICS
awslocal sns create-topic --name topico_pagamento_pendente && \
awslocal sns create-topic --attributes FifoTopic=true --name topico_producao.fifo && \
awslocal sns create-topic --name topico_pagamento_retorno && \

### QUEUES
awslocal sqs create-queue --queue-name fila_pagamento_pendente && \
awslocal sqs create-queue --attributes FifoQueue=true --queue-name fila_producao.fifo && \
awslocal sqs create-queue --attributes FifoQueue=true --queue-name fila_pedido_pronto.fifo && \
awslocal sqs create-queue --queue-name fila_pagamento_aprovado && \
awslocal sqs create-queue --queue-name fila_pagamento_recusado && \
awslocal sqs create-queue --queue-name fila_pagamento_cancelado && \

### SUBSCRIPTIONS
awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:topico_pagamento_pendente" \
  --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:fila_pagamento_pendente" \
  --attributes '{"RawMessageDelivery": "true"}' && \

awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:topico_pagamento_retorno" \
  --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:fila_pagamento_aprovado" \
  --attributes '{"RawMessageDelivery": "true", "FilterPolicy":"{\"status\":[\"PAGO\"]}", "FilterPolicyScope":"MessageBody"}' && \

awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:topico_pagamento_retorno" \
  --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:fila_pagamento_recusado" \
  --attributes '{"RawMessageDelivery": "true", "FilterPolicy":"{\"status\":[\"RECUSADO\"]}", "FilterPolicyScope":"MessageBody"}' && \

awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:topico_pagamento_retorno" \
  --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:fila_pagamento_cancelado" \
  --attributes '{"RawMessageDelivery": "true", "FilterPolicy":"{\"status\":[\"CANCELADO\"]}", "FilterPolicyScope":"MessageBody"}' && \

awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:topico_producao.fifo" \
  --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:fila_producao.fifo" \
  --attributes '{"RawMessageDelivery": "true", "FilterPolicy":"{\"status\":[\"EM_PREPARACAO\"]}", "FilterPolicyScope":"MessageBody"}' && \

awslocal sns subscribe --topic-arn "arn:aws:sns:us-east-1:000000000000:topico_producao.fifo" \
  --protocol sqs --notification-endpoint "arn:aws:sqs:us-east-1:000000000000:fila_pedido_pronto.fifo" \
  --attributes '{"RawMessageDelivery": "true", "FilterPolicy":"{\"status\":[\"PRONTO\"]}", "FilterPolicyScope":"MessageBody"}'
