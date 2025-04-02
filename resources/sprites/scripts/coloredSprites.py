# if you need to install pillow run following code in command line
# python3 -m pip install --upgrade pip
# python3 -m pip install --upgrade Pillow

from PIL import Image
from os import mkdir

# open all sprite sheet
sheet1 = Image.open('sheets/babaSheetObjects.png')
sheet2 = Image.open('sheets/babaSheetObjects2.png')
sheet3 = Image.open('sheets/babaSheetObjects3.png')
sheet4 = Image.open('sheets/babaSheetObjects4.png')
sheet5 = Image.open('sheets/babaSheetObjects5.png')
sheet6 = Image.open('sheets/babaSheetObjects6.png')

# -------------------------------------------------------------------
#
# Extracts a 3 state, vertically oriented, animated sprite
#
# -------------------------------------------------------------------
def extractSprite3(filename, left, top, sheet, colors, size):
  posX = size * left
  posY = size * top

  name = filename.replace(".png", "")
  for i in range(3):
    sprite = sheet.crop((posX, posY + i * size, posX + size, posY + (i + 1) * size))
    
    # Convert sprite to RGBA mode for transparency
    sprite = sprite.convert('RGBA')
    
    # Get the pixel data
    pixels = sprite.load()
    
    # Loop through each pixel
    for x in range(sprite.width):
        for y in range(sprite.height):
            r, g, b, a = pixels[x, y]
            
            # Check if the pixel matches any of our green colors
            for green_r, green_g, green_b in colors:
                if r == green_r and g == green_g and b == green_b:
                    # Make the pixel transparent
                    pixels[x, y] = (0, 0, 0, 0)
                    break
            print(r, g, b, a)

    sprite.save(f'sprites/{name}_{i}.png')
  
  spriteSheet = Image.new('RGBA', (3 * size, size))
  for i in range(3):
    spriteSheet.paste(Image.open(f'sprites/{name}_{i}.png'), ((i * size, 0)))
  spriteSheet.save(filename)

# LAVA 36
# WALL 60
# WATER 63

# -------------------------------------------------------------------
#
# Extract all the object types required for the CS 5410 game
#
# -------------------------------------------------------------------
## List of green background colors to check (in RGB format)
## sheet, col, row
# extractSprite3('water.png', 19, 63, sheet4, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('lava.png', 19, 36, sheet5, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('hedge.png', 19, 30, sheet5, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('wall.png',  19, 91, sheet5, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51), (0, 0, 255)], 25)
# extractSprite3('grass.png',19, 27, sheet5, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('flag.png',  4, 9, sheet2,[(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('flowers.png', 9, 9, sheet2,[(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('rock.png', 9, 24, sheet2, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('floor.png', 11, 27, sheet2, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)

##### DIDN'T WORK FOR ROCK and FLOOR



# # -------------------------------------------------------------------
# #
# # Extract all the word types required for the CS 5410 game
# #
# # -------------------------------------------------------------------
# extractSprite3('word-wall.png', 18, 60, sheet4, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-rock.png', 8, 24, sheet1, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-flag.png', 3, 9, sheet2,[(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-baba.png', 22, 0, sheet6, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-is.png', 9, 3, sheet3, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-stop.png', 6, 12, sheet3, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-push.png', 2, 12, sheet3, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-lava.png', 17, 36, sheet5, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-water.png', 17, 63, sheet4, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-you.png', 10, 9, sheet3, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25)
# extractSprite3('word-win.png', 11, 45, sheet3, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25) ## Not quite right
# extractSprite3('word-sink.png', 3, 152, sheet3, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25) ## Not quite right
# extractSprite3('word-kill.png', 6, 149, sheet3, [(84, 165, 75),(58, 100, 51),(27, 89, 153),(58, 111, 51)], 25) ## Not quite right

## Didn't work


sheet1.close()
sheet2.close()
sheet3.close()
sheet4.close()
sheet5.close()