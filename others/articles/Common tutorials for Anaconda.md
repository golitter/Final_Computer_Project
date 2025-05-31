### View All Current Environments

```shell
conda info --envs
-- or
conda env list
```

### Create a Virtual Environment

Create a virtual environment named `env_name` with Python version `beta`.

```shell
conda create -n env_name [ python=beta ]
```

### Delete a Virtual Environment

```shell
conda remove -n env_name --all
```

#### Activate a Virtual Environment

```shell
conda activate env_name
```

#### Deactivate a Virtual Environment

```shell
conda deactivate env_name
```

#### Package Operations Within a Virtual Environment

##### If you are not in the virtual environment where you want to install packages

```shell
# View installed packages in a specified environment
conda list -n env_name
# Install a package in a specified environment
conda install -n env_name [package]
# Remove a package from a specified environment
conda remove -n env_name [package]
# Update a package in a specified environment
conda update -n env_name [package]
```

##### If you are in the virtual environment where you want to install packages

```shell
# View installed packages
conda list
# Install a package
conda install [package]
# Remove a package
conda remove [package]
# Update a package
conda update [package]

# Update conda to keep it up-to-date
conda update conda
```

##### Export Virtual Environment to a YAML File

```shell
conda env export > environment.yaml -- Export
conda env create -f environment.yaml -- Import
```

##### Use pip to Export and Import Package Information for a Virtual Environment

```shell
pip freeze > requirements.txt -- Export
pip install -r requirements.txt -- Import
```