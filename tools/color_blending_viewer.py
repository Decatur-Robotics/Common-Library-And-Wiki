import pygame
import time


inp = input()
ind = 0
colors = inp.split("|")

pygame.init()

screen_width = 400
screen_height = 400

screen = pygame.display.set_mode((screen_width, screen_height))

running = True
while running:
    color = colors[ind].split(", ")
    screen.fill( (int(color[0]), int(color[1]), int(color[2])) )
    pygame.display.flip()
    ind += 1
    if ind >= len(colors):
        ind = 0
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
    time.sleep(1/60)

pygame.quit()
