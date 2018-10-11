from django.core.files.storage import FileSystemStorage
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

@method_decorator(csrf_exempt, name='dispatch')
def upload(request):
    print("POST")

    if request.method == 'POST':
        print(request.POST)
        myfile = request.FILES['myfile']
        print(myfile)
        fs = FileSystemStorage()
        filename = fs.save(myfile.name, myfile)
        uploaded_file_url = fs.url(filename)
        return render(request, '../templates/upload.html', {
            'uploaded_file_url': uploaded_file_url
        })
    return render(request,  '../templates/upload.html')