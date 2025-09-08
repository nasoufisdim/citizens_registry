variable "region" {
  description = "The AWS region to deploy to"
  type        = string
  default     = "us-east-1"
}

variable "instance_type_app" {
  description = "EC2 instance type for the Spring Boot application"
  type        = string
  default     = "t2.micro"
}

variable "instance_type_db" {
  description = "EC2 instance type for the Citizen DB"
  type        = string
  default     = "t3.medium"
}

variable "db_user" {
  description = "Username for the MySQL database"
  type        = string
  default   = "appuser"
}

variable "db_password" {
  description = "Password for the MySQL database"
  type        = string
  sensitive   = true
}

variable "db_name" {
  description = "Name of the MySQL database"
  type        = string
  default     = "citizens"
}

variable "vpc_id" {
  description = "VPC ID for the infrastructure"
  type        = string
  default     = "vpc-070bb339e1faa3f45"
}

#variable "subnets" {
 # description = "Subnets for the load balancer"
#  type        = list(string)
#  default     = data.aws_subnets.default.ids
#}

variable "key_name" {
  description = "The name of the SSH key pair"
  type        = string
  default     = "terra-test"
}

variable "jar_name" {
  description = "The name of the app jar file"
  type        = string
  default     = "citizen-spring-0.1"
}