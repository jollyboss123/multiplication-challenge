app:
	docker-compose -f services/docker/app.yml up -d

rabbit:
	docker-compose -f services/docker/rabbitmq.yml up -d

consul:
	docker-compose -f services/docker/consul.yml up -d
