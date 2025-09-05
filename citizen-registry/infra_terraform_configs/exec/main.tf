provider "aws" {
  region = var.region
}

resource "aws_vpc" "main" {
  cidr_block = var.vpc_cidr
}

resource "aws_subnet" "main" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = var.subnet_cidr
  availability_zone = "${var.region}a"
}

resource "aws_security_group" "app_sg" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port   = var.app_port
    to_port     = var.app_port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = var.db_port
    to_port     = var.db_port
    protocol    = "tcp"
    cidr_blocks = [var.vpc_cidr]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "db" {
  ami           = var.db_ami
  instance_type = var.db_instance_type
  subnet_id     = aws_subnet.main.id
  security_groups = [aws_security_group.app_sg.id]
}

resource "aws_instance" "app" {
  count         = var.app_instance_count
  ami           = var.app_ami
  instance_type = var.app_instance_type
  subnet_id     = aws_subnet.main.id
  security_groups = [aws_security_group.app_sg.id]
}

resource "aws_elb" "app_lb" {
  name               = "app-lb"
  availability_zones = ["${var.region}a"]

  listener {
    instance_port     = var.app_port
    instance_protocol = "HTTP"
    lb_port           = 80
    lb_protocol       = "HTTP"
  }

  instances = aws_instance.app[*].id
}
