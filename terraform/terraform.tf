provider "aws" {
  region = "us-east-1"
}

resource "aws_security_group" "ms60min" {
  name        = "ms60min"
  description = "Used by terraform"

  # SSH access from anywhere
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # HTTP access from anywhere
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # outbound internet access
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "web" {
  instance_type = "t2.micro"
  ami = "ami-f4cc1de2"  # Ubuntu Server 16.04
  security_groups = ["${aws_security_group.ms60min.name}"]
  key_name = "microservice-60min"

  tags {
    Name = "microservice-60min"
  }

  connection {
    user = "ubuntu"
    private_key = "${file("microservice-60min.pem")}"
  }

  provisioner "remote-exec" {
    inline = [
      "sudo apt-get install apt-transport-https ca-certificates curl software-properties-common",
      "curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -",
      "sudo add-apt-repository 'deb [arch=amd64] https://download.docker.com/linux/ubuntu xenial stable'",
      "sudo apt-get update",
      "sudo apt-get -y install docker-ce",
      "sudo docker run -d -p 80:8080 kaitoedter/ms60min",
    ]
  }
}

output "public ip address" {
  value = "${aws_instance.web.public_ip}"
}