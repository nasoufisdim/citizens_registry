# ----- General -----
variable "region" {
  description = "Περιοχή AWS όπου θα δημιουργηθεί η υποδομή"
  type        = string
  default     = "eu-central-1"
}

variable "vpc_cidr" {
  description = "CIDR block για το VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "subnet_cidr" {
  description = "CIDR block για το subnet"
  type        = string
  default     = "10.0.1.0/24"
}

# ----- Security -----
variable "app_port" {
  description = "Πόρτα εφαρμογής (RESTful service)"
  type        = number
  default     = 8080
}

variable "db_port" {
  description = "Πόρτα ΣΔΒΔ (π.χ. MySQL)"
  type        = number
  default     = 3306
}

# ----- Instances -----
variable "app_instance_count" {
  description = "Αριθμός στιγμιότυπων για την RESTful υπηρεσία"
  type        = number
  default     = 3
}

variable "app_instance_type" {
  description = "Τύπος instance για RESTful υπηρεσία"
  type        = string
  default     = "t2.micro"
}

variable "db_instance_type" {
  description = "Τύπος instance για ΣΔΒΔ"
  type        = string
  default     = "t2.micro"
}

# ----- Images (AMI IDs) -----
variable "app_ami" {
  description = "AMI ID για την RESTful εφαρμογή"
  type        = string
}

variable "db_ami" {
  description = "AMI ID για το ΣΔΒΔ"
  type        = string
}
