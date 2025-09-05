provider "aws" {
  region = "eu-central-1" # π.χ. Frankfurt
}

# Δημιουργία custom image (AMI) από υπάρχουσα VM
resource "aws_ami_from_instance" "app_image" {
  name               = "citizen-registry-image"
  source_instance_id = "i-xxxxxxxxxxxx"  # ID από VM που τρέχει την υπηρεσία
}

output "app_image_id" {
  value = aws_ami_from_instance.app_image.id
}
